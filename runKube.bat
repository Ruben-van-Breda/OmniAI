echo Maven install
call mvn clean install

echo Building images

docker-compose build

echo Starting minikube
minikube start --kubernetes-version=v1.26.3

echo Loading images into minikube. This may take a while...

echo omni-broker:latest
minikube image load omni-broker:latest
echo omni-chatbot1:latest
minikube image load omni-chatbot1:latest
echo omni-chatbot2:latest
minikube image load omni-chatbot2:latest
echo omni-chatbot3:latest
minikube image load omni-chatbot3:latest
echo omni-chatbot4:latest
minikube image load omni-chatbot4:latest
echo omni-web-client:latest
minikube image load omni-web-client:latest

echo Applying api-keys as environment variables
kubectl create secret generic api-keys --from-env-file=api-keys.txt

echo Deploying to Kubernetes
kubectl apply -f .\kubekompose.yaml

echo Waiting for pods to be ready
kubectl wait --for=condition=Ready pod --all

echo Opening the application
minikube service web-client


