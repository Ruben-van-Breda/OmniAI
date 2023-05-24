package main

import (
	"context"
	"encoding/json"
	"fmt"
	"html/template"
	"io/ioutil"
	"net/http"
	// "strconv"
	"strings"
	"os"

	openai "github.com/sashabaranov/go-openai"
)




var api_key string = "API_KEY"
var reference int = 0

func main() {
	api_key = os.Getenv("TURBO")
	http.HandleFunc("/", handleChat)
	http.HandleFunc("/chat/query", handleChatQuery)

	http.ListenAndServe(":8084", nil)

}

func handleChatQuery(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodPost {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		fmt.Println("Error:", http.StatusMethodNotAllowed)
		return
	}

	body, err := ioutil.ReadAll(r.Body)
	if err != nil {
		http.Error(w, "Error reading request body", http.StatusInternalServerError)
		return
	}
	fmt.Println("Handling POST Request on Path: /chat/query")
	defer r.Body.Close()

	var chatQuery ChatQuery

	err = json.Unmarshal(body, &chatQuery)
	if err != nil {
		http.Error(w, "Error parsing request body", http.StatusBadRequest)
		return
	}
	query := chatQuery.Query
	fmt.Println("Query:", query)

	// referenceString := strconv.Itoa(reference)
	referenceString := "Turbo"
	reference++
	response := ChatQueryResponse{
		Reference:    referenceString,
		QueryResponse: Prompt(query),
	}

	// Set the Content-Type header to application/json.
	w.Header().Set("Content-Type", "application/json")

	// Encode the response object as JSON and write it to the response writer.
	json.NewEncoder(w).Encode(response)

}

func handleChat(w http.ResponseWriter, r *http.Request) {
	if r.Method == http.MethodPost {
		input := r.FormValue("input")
		result := Prompt(input)

		tmpl := template.Must(template.ParseFiles("index.html"))
		tmpl.Execute(w, map[string]interface{}{
			"Result": result,
		})
	} else {
		tmpl := template.Must(template.ParseFiles("index.html"))
		tmpl.Execute(w, nil)
	}
}

func Prompt(prompt string) string {
	client := openai.NewClient(api_key)
	resp, err := client.CreateChatCompletion(
		context.Background(),
		openai.ChatCompletionRequest{
			Model: openai.GPT3Dot5Turbo,
			Messages: []openai.ChatCompletionMessage{
				{
					Role:    openai.ChatMessageRoleUser,
					Content: prompt,
				},
			},
		},
	)

	if err != nil {
		errStr := err.Error()
		if strings.Contains(errStr, "error") || strings.Contains(errStr, "429") {
			return "Sorry unfortunately I am not able to answer your question right now."
		}
		return errStr
	}
	return resp.Choices[0].Message.Content
}

type ChatQueryResponse struct {
	Reference     string `json:"reference"`
	QueryResponse string `json:"queryResponse"`
}

type ChatQuery struct {
	Query string `json:"query"`
}
