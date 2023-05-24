FROM node:14

WORKDIR /app

COPY package.json package-lock.json ./

RUN npm ci

ENV API_KEY=$API_KEY

COPY . .

EXPOSE 8083


CMD ["node", "index.js"]