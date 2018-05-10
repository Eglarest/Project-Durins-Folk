# Argonath
  > Login / Gate keeper

  ### TODO
  - [ ] Build / configure `nginx` reverse proxy outward facing server
  - [x] Setup gatekeeping application for user accounts

### Getting started
  - install `node`
  - install `npm`
  - from `Argonath` run
    - `npm run init`
    - For development with automatic reloading on file change `npm run start:dev`
    - For basic `npm start`

#### Creating certificates with openssl
  + Generate Private Key
   - `openssl genrsa -des3 -out auth.key 1024`
  + Generate a Certificate Signing Request (CSR)
   - `openssl req -new -key auth.key -out auth.csr`
  + Generate a Self-Signed SSL Certificate
   - `openssl x509 -req -days 365 -in auth.csr -signkey auth.key -out auth.cr`
