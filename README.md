# retailRewards API
Retail rewards program for customers. API allows to view customer data, their reward points. Also, allows to add new customer.
A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.
**##Feature**
- ** Calculate rewardPoints** for each customer based on transaction.
- Customer has unique phoneNumber
- Retrive customer details and their transactions
- Add new customer and their transaction
- -RESTful API build using SpringBoot and JPA

  ##Reward Calculation
  -A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent between $50 and $100 in each transaction.
  -Example:
    a $120 purchase = 2x$20 + 1x$50 = 90 points)

  ##API Endpoints
  ##GET cutomer data
  http://localhost:8080/retail/{phoneNumber}
  Response:
  {
    "customer": {
        "phoneNumber": "1234567890",
        "name": "Ashley Graham",
        "transactions": [
            {
                "id": 1,
                "amount": 120.00,
                "date": "2025-03-03"
            },
            {
                "id": 2,
                "amount": 150.00,
                "date": "2025-02-03"
            },
            {
                "id": 3,
                "amount": 110.00,
                "date": "2025-01-03"
            }
        ]
    },
    "transactions": [
        {
            "id": 1,
            "amount": 120.00,
            "date": "2025-03-03"
        },
        {
            "id": 2,
            "amount": 150.00,
            "date": "2025-02-03"
        },
        {
            "id": 3,
            "amount": 110.00,
            "date": "2025-01-03"
        }
    ],
    "rewardPoints": {
        "JANUARY": 70,
        "MARCH": 90,
        "FEBRUARY": 150,
        "Total": 310
    }

}
##GET rewards point
http://localhost:8080/retail/rewards/{phoneNumber}
Response:
{
    "JANUARY": 70,
    "MARCH": 90,
    "FEBRUARY": 150,
    "Total": 310
}



