// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract ProductOrderContract {

    struct Product {
        uint256 productId;
        string name;
        uint256 quantity;
        address buyer;
        address farmer;
        bool delivered;
        string deliveryLocation;
        uint256 orderDate;
        uint8 prices;
    }

    mapping(uint256 => Product) public products;
    uint256 public productCount;

    mapping(address => uint256[]) public shoppingCart;


    function orderProduct(uint256[] memory _productIds, string[] memory _names, uint256[] memory _quantities, address[] memory _farmers, string memory _deliveryLocation,uint8[] memory _prices) external payable {
        require(_productIds.length == _names.length && _names.length == _quantities.length && _quantities.length == _farmers.length, "Invalid input lengths.");
        require(msg.value > 0, "Payment required.");

        uint256 totalPayment = 0;
        uint256 orderDate = block.timestamp;
        // Current block timestamp as the order date

        for (uint256 i = 0; i < _productIds.length; i++) {
            productCount++;
            products[productCount] = Product(productCount, _names[i], _quantities[i], msg.sender, _farmers[i], false, _deliveryLocation, orderDate,_prices[i]);
            shoppingCart[msg.sender].push(productCount);


            totalPayment += _quantities[i];
            // Assuming each product has a unit price of 1 Ether or any other currency.
        }

        require(msg.value >= totalPayment, "Insufficient payment.");
    }

    function addFarmerAddress(uint256 _productId, address _farmer) external {

        require(_productId <= productCount, "Invalid productId.");
        Product storage product = products[_productId];
        require(product.buyer == msg.sender, "Only the buyer can add the farmer address.");
        product.farmer = _farmer;

    }

    function deliverProduct(uint256 _productId) external {


        require(products[_productId].buyer == msg.sender, "Only the buyer can mark the product as delivered.");
        Product storage product = products[_productId];
        require(!product.delivered, "The product has already been delivered.");

        product.delivered = true;

        address payable farmerAddress = payable(product.farmer);
        uint256 paymentAmount = product.quantity;
        // Assuming each product has a unit price of 1 Ether or any other currency.

        // Transfer funds to the farmer
        farmerAddress.transfer(paymentAmount);

        // Check if the product was not delivered and initiate a refund
        if (!product.delivered) {
            // Transfer funds back to the buyer
            address payable buyerAddress = payable(product.buyer);
            buyerAddress.transfer(paymentAmount);
        }
    }

    function getShoppingCart(address _buyer) external view returns (uint256[] memory) {
        return shoppingCart[_buyer];
    }

    function getProductData(uint256 _productId) external view returns (uint256, string memory, uint256, address, address, bool, string memory, uint256) {
        require(_productId <= productCount, "Invalid productId.");

        Product storage product = products[_productId];

        return (
        product.productId,
        product.name,
        product.quantity,
        product.buyer,
        product.farmer,
        product.delivered,
        product.deliveryLocation,
        product.orderDate
        );
    }
}
