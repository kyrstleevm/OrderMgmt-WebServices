# OrderMgmt-WebServices
This Spring Boot RESTful web service is designed for drop-shipping retailers to synchronize local operations with an external wholesaler. It bridges the gap between customer-facing retail and administrative management using JPA and an H2 database.

Core Functionalities
The system features two distinct interfaces:


Customer Client: Users can browse "on-sale" products, place orders, view history, and cancel pending transactions.

Operator Client: A secured administrative panel for importing products from a wholesaler API, updating retail prices, and managing order statuses.


Technical Achievements

HATEOAS Integration: Dynamically navigates the external wholesaler’s API, ensuring resilience against structural changes in the external service.

RESTful Design: Adheres to statelessness and uniform interface standards using JSON for all resource representations.


Security & Business Logic: Employs prompt-based authorization (Stir@123) for administrative tasks and includes real-time customer revenue tracking.


By decoupling local retail prices from wholesaler costs while maintaining live stock monitoring, the system resolves common drop-shipping synchronization challenges.
