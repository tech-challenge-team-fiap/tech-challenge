# DataBase - Justification

We identified that for the business solution, it was necessary to have a database that had excellent management control between tables, as well as offering advanced query optimization and efficient indexing features, ensuring fast responses. 
Therefore, we chose to use MySQL as a solid choice for a monolithic system due to its performance, integrity, scalability, and advanced security features. These characteristics reliably and scalably meet business needs.

As we enter Phase 3, the project is evolving towards a transition to a microservices and cloud ecosystem. Therefore, we decided to use AWS RDS due to its native support for MySQL, avoiding extensive migration rework and ensuring seamless integration of the database. 
The transition to AWS RDS carries a low risk of failures and a significant gain in high availability, automatic scalability, and ease of integration with other AWS ecosystem services that will be used by the project.

## Entity Relationship Model
![image](/docs/datamodel/ER-entityrelationshipmodel.png)

