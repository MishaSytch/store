# Endpoints

- account
  - customer
    - {id} [**get**]
      - add
        - order [**post**]
      - delete
        - order [**get** **del**]
      - orders [**get**]

- auth
  - admin
    - sing-up [**post**]
  - sign-in [**post**]
  - sign-up [**post**]

- category
  - all [**get**]
  - {id} [**get**]
    - product
      - all [**get**]

- product [**post**]
  - all [**get**]
  - update
    - image
      - {id} [**put**]
    - price
      - {id} [**put**]
  - {id} [**get** **put** **del**]
  - add
    - image [**post**]
    - price [**post**]
  - create
      - image [**post**]
      - price [**post**]
  - delete
      - image [**post**]
      - price [**post**]
  - quantity [**get** **put**]

- order
  - create [**post**]