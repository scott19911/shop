<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="orderId" required="true" rtexprvalue="true" %>
<div class="cart-collaterals" id="${orderId}">
    <h2><a onclick="hiddenAllEmptyFields()" class="shipping-calculator-button"
           data-toggle="collapse"
           href="#calcalute-shipping-wrap${orderId}" aria-expanded="false"
           aria-controls="calcalute-shipping-wrap${orderId}" id="${orderId}"><fmt:message key="details"/></a></h2>

    <section id="calcalute-shipping-wrap${orderId}" class="shipping-calculator-form collapse" >
        <table cellspacing="0" class="shop_table cart">
                <thead>
                <tr>z
                    <th class="product-thumbnail">&nbsp;</th>
                    <th class="product-name"><fmt:message key="product"/></th>
                    <th class="product-price"><fmt:message key="price"/></th>
                    <th class="product-quantity"><fmt:message key="quantity"/></th>
                    <th class="product-subtotal"><fmt:message key="total"/>Total</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="entry" items="${sessionScope.orderInfo.getOrderProduct().get(orderId).entrySet()}">
                    <tr class="cart_item">
                        <td class="product-thumbnail">
                            <a href="single-product.html"><img width="445" height="445"
                                                               alt="poster_1_up"
                                                               class="shop_thumbnail"
                                                               src="/drawAvatar?productImg=${entry.getKey().imgUrl}"></a>
                        </td>
                        <td class="product-name">
                            <a href="single-product.html">${entry.getKey().name}</a>
                        </td>

                        <td class="product-price">
                            <span class="amount">${entry.getKey().price}</span>
                        </td>

                        <td class="product-quantity">
                                ${entry.getValue()}
                            <p id="errorProduct${entry.getKey().productId}" style="color:red"> ${sessionScope.get("error").get("errorProduct".concat(entry.getKey().productId))}
                            </p>
                        </td>

                        <td class="product-subtotal">
                            <span class="amount">${entry.getKey().price * entry.getValue()}</span>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
        </table>
    </section>
</div>