FROM golang:1.23

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install -y protobuf-compiler \
    && go install google.golang.org/protobuf/cmd/protoc-gen-go@latest \
    && go install google.golang.org/grpc/cmd/protoc-gen-go-grpc@latest

RUN protoc --go_out=. --go-grpc_out=. ./proto/notification.proto

RUN go mod tidy
RUN go build -o notification-service ./cmd/main.go

EXPOSE 50055

CMD ["./notification-service"]