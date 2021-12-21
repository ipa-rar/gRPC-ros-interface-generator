# gRPC-ros-interface-generator

## Protoc descriptor file generation
`protoc --descriptor_set_out=protoc/demo.proto protobufs/demo.proto`
## Docker build the generator

`docker build -t grpc_generator -f docker/Dockerfile ./`
