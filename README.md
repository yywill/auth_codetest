# Design & coding test

## Architecture

``` bash
┌────────────────────────────────┐
│ Gateway Layer（skipped          │
│                                │
│   HTTP/Rest etc... skiped      │
│                                │
├────────────────────────────────┤
│ Service Layer： Auth Service    ├─────┐
│            ┌───────────────────┤     │
│            │Session Management：│     │
│            │                   │     │
├────────────┴───────────────────┼─────▼────┐
│ Repo Layer： User/Role Service  │ utils：   │
│                                │          │
│                                │          │
│                                │ Token... │
├────────────────────────────────┤          │
│ Domain Layer： User/Role        │          │
│                                │          │
│                                │          │
│                                │          │
└────────────────────────────────┴──────────┘
```

## Design Considerations & Dependencies

Rich Domain Modeling

* User.roles --> a set in user
* token 
  * could be invalidated, means: token should be maintained in a stateful collections in serverside
  * could get user from token, means: userName encoded in token
  
### compile scope

1. gson for serializable
2. sl4j for logging

### test scope

1. Junit for test
2. aspectJ(assertThat) for better testing

## TDD Test Cases

1. Util Test(Util Layer)
   1. generate token
   2. parse token 
2. Role Test(Internal Service Layer)
   1. create role
   2. delete role
   3. get role
3. User Test(Internal Service Layer)
   1. create user
   2. delete user
   3. get user
4. Auth Test(API Layer)
   1. add role to a user 
   2. auth(login) --> get token
   3. invalid --> remove token in session store
   4. get all roles by token
   5. check token & single role
   6. ttl config as parameters of getInstance()

