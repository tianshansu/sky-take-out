This project is a food ordering system based on Spring Boot, consisting of a management platform (web) and a user platform (WeChat mini-program). Merchants use the management platform to handle menu items, meal packages, employee management, order processing, and order statistics, while users place orders and track order status through the WeChat mini-program.
Key Features:

JWT Authentication: Used JWT tokens for secure user login.
Redis Caching: Improved access speed with Redis.
OSS Storage: Stored food images and menu assets.
Automatic Data Filling: Used AOP to auto-fill common fields (e.g., timestamps).
Delayed Order Handling: Used Redisson to auto-cancel overdue orders.
Real-time Notifications: Implemented WebSocket for order reminders.
