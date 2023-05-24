FROM golang:1.19-alpine

WORKDIR /app

COPY go.mod go.sum ./
RUN go mod download

COPY *.go ./

COPY index.html .

RUN go build -o /main

ENV API_KEY=$API_KEY

EXPOSE 8084

CMD [ "/main" ]
