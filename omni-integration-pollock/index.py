import os
import flask
import AI_BOT

app = flask.Flask(__name__)
port = os.environ.get('PORT', 8087)

@app.route('/')
def index():
    return "Pollock Bot is running"

@app.route('/', methods=['POST'])
def queryBot():
    print("Query received")
    if flask.request.method == 'POST':
        query = flask.request.form['query']
        print(query)
        response, prompts = AI_BOT.Prompt(query)
        response = response.strip()
        print("Sending response: " + response)
        print("Prompts: " + str(prompts))
    return response

@app.route('/chat/query', methods=['POST'])
def botQuery():
    try:
        data = flask.request.get_json()
        query = data['query']
        response = AI_BOT.Prompt(query)
        response = response.strip()

        response_data = {
            "reference" : "Pollock",
            "queryResponse" : response
        }

        return flask.jsonify(response_data)
    except Exception as e:
        return flask.jsonify(e)

@app.route('/chat/image', methods=['POST'])
def queryBotImage():
    print("Image Query received")
    if flask.request.method == 'POST':
        query = flask.request.get_json()['query']
        query += " in the style of Jackson Pollock"
        print(query)
        response = AI_BOT.ImagePrompt(query)
        response = response.strip()
        print("Sending response: " + response)

        # Load default image 
        if response == "":
            response = "https://i.imgur.com/2Q2Qz0I.png"
        

        response_data = {
            "reference" : "Pollock",
            "queryResponse" : response
        }
    return flask.jsonify(response_data)


if __name__ == '__main__':
    print("Starting Davinci server on port: " + str(port))
    app.run(debug=True, host='0.0.0.0', port=port)
