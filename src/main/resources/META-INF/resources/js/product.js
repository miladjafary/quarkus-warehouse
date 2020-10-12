let productPage = {
    hiddenIntro: function () {
        $("#introPage").hide();
    },
    showIntro: function () {
        $("#introPage").removeClass("d-none");
        $("#introPage").addClass("d-block");
    },
    renderProducts: function (products) {
        let _this = this;
        $("#productsContainer").html("");
        products.forEach(function (product) {
            _this.renderProductElement(product);
        })
    },
    loadProducts: function () {
        let _this = this;
        $.ajax({
            url: "/product/list",
            success: function (products) {
                if (products.length === 0) {
                    _this.showIntro();
                    $("#productsContainer").html("<h5 class=\"col-md-12\">There is not any product in warehouse!</h5>")
                } else {
                    _this.renderProducts(products)
                }
            }
        });
    },
    sellProduct: function (productId) {
        let _this = this;
        _this.hideAlertContainer();
        $.ajax({
            url: "/product/sell/" + productId,
            type: 'PUT',
            success: function (result) {
                _this.loadProducts();
                _this.showSuccessMessage("Product successfully has been sold!")
            },
            error: function (errorObject) {
                if (errorObject.status === 400) {
                    let errors = errorObject.responseJSON;
                    _this.renderErrors(errors);
                }
            }
        });
    },
    showSuccessMessage: function (message){
        this.showAlertContainer();
        let successMessageElement = `
            <div class="alert alert-success alert-dismissible fade show" role="alert">
              ${message}
              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
        `;
        $("#alertContainer").html(successMessageElement);
    },
    renderErrors: function (errors) {
        let _this = this;
        let errorsElement = "";
        _this.showAlertContainer();
        errors.forEach(function (error) {
            errorsElement += _this.createErrorElement(error);
        });

        $("#alertContainer").html(errorsElement);
    },
    createErrorElement: function (error) {
        let description = error.description;

        let errorElement = `
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
              ${description}
              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
        `;

        return errorElement;
    },
    showAlertContainer: function () {
        $("#alertContainer").remove("d-none");
        $("#alertContainer").addClass("d-block");
    },
    hideAlertContainer: function () {
        $("#alertContainer").html("");
        $("#alertContainer").remove("d-block");
        $("#alertContainer").addClass("d-none");
    },
    renderProductElement: function (product) {
        let _this = this;
        let productId = product.id;
        let productName = product.name;
        let quantity = product.quantity;
        let articles = "";
        product.contain_articles.forEach(function (productArticle) {
            articles += _this.createArticle(productArticle);
        });

        let productElement = `<div class="col-sm-3 ">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">${productName}</h5>
                    <h6 class="card-subtitle mb-2 text-muted">Articles</h6>
                </div>
                <ul class="list-group list-group-flush">${articles}</ul>
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <a href="#" class="btn btn-primary" onclick="productPage.sellProduct(${productId});">Sell</a>
                        <a href="#" class="btn btn-primary">Quantity: ${quantity}</a>
                    </div>
                </div>
            </div>
        </div>`;

        $("#productsContainer").append(productElement);
    },

    createArticle: function (productArticle) {
        let articleName = productArticle.article_name;
        let articleAmount = productArticle.amount_of;
        return `
            <li class="list-group-item d-flex justify-content-between align-items-center">
                ${articleName}
                <span class="badge badge-primary badge-pill">${articleAmount}</span>
            </li>
        `;
    }
};


$(function () {
    productPage.loadProducts();
})