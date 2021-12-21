# gRPC-ros-interface-generator

## Protoc descriptor file generation
Repeat this process for every new proto file added to the protobufs directory
- `protoc --descriptor_set_out=protoc/_filename_.ds protobufs/_filename_.proto`

## Docker run the generator

1. `docker build -t grpc_generator -f docker/Dockerfile ./`

2. `docker run grpc_generator:latest`
