package main

import (
	"fmt"
	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"
	"log"
	"net"
	service "notification-service/internal/service"
	pb "notification-service/proto/generated"
	"os"
)

func main() {
	lis, err := net.Listen("tcp", fmt.Sprintf(":%s", os.Getenv("GRPC_SERVER_PORT")))
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}

	s := grpc.NewServer()

	pb.RegisterNotificationServiceServer(s, &service.NotificationServiceImpl{})

	reflection.Register(s)

	log.Printf("gRPC server is running on port %s", os.Getenv("GRPC_SERVER_PORT"))
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
