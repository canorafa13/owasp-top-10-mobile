FROM node:16-alpine3.11 as build
WORKDIR /app
COPY ./package.json /app/package.json
RUN npm install --only production
COPY . /app

FROM node:16-alpine3.11
WORKDIR /app
COPY --from=build /app /app