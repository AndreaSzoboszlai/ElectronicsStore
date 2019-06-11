\# About

Quickstart repo for a Maven managed AJAX web-application using Servlets and JDBC.

There are three roles:
	- Admin
	- Employee
	- Customer

Employees can add new products to the web page and delete products when it's not in a cart or order, they can also add coupons and can see all orders and mark selected as shipped.
Admins have the same functions but with the addition of also adding users to the system, and seeing all user and their datas.
Customers can see the products and put them into their carts, they can use coupon codes for their cart and place orders.
Coupons: only employees and admins can add coupons to the store and delete them and they are available till they are not deleted, one purchase can only use one coupon.

Before deploying to a webserver create a `Resource` like in your webserver's config (e.g. for Apache Tomcat in `conf/context.xml`).

```
	<Resource name="jdbc/electronicsStore"
		  type="javax.sql.DataSource"
		  username="postgres"
		  password="admin"
		  driverClassName="org.postgresql.Driver"
		  url="jdbc:postgresql://localhost:5432/electronics_store"
		  closeMethod="close"/>
```

*Note*: the `closeMethod="close"` attribute is important. [As per Tomcat's documentation][1] this ensures that connections retrieved from the datasource are closed properly when a webapp context is reloaded/restarted/redeployed/etc.

[1]: https://tomcat.apache.org/tomcat-9.0-doc/config/context.html\#Resource_Definitions
