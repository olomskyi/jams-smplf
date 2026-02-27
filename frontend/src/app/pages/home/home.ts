import { AsyncPipe, JsonPipe } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { OrderService } from '../../services/orderService';
import { ProductService } from '../../services/productService';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { OrderModel } from '../../model/orderModel';
import { ProductModel } from '../../model/productModel';

@Component({
  selector: 'app-home',
  imports: [AsyncPipe, JsonPipe, FormsModule],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home implements OnInit {
  private readonly oidcSecurityService = inject(OidcSecurityService);
  private readonly productService = inject(ProductService);
  private readonly orderService = inject(OrderService);
  private readonly router = inject(Router);
  isAuthenticated = false;
  products: Array<ProductModel> = [];
  quantityIsNull = false;
  orderSuccess = false;
  orderFailed = false;

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(
      ({isAuthenticated}) => {
        this.isAuthenticated = isAuthenticated;
        this.productService.getProducts()
          .pipe()
          .subscribe(product => {
            this.products = product;
          })
      })
  }

  createOrder(product: ProductModel, quantity: number): void {

    this.oidcSecurityService.userData$.subscribe(result => {
      console.log(result);
      const userDetails = {
        email: result.userData.preferred_username,
        firstName: result.userData.given_name,
        lastName: result.userData.family_name
      };

      if (quantity == null || quantity <= 0) {
        this.orderFailed = true;
        this.orderSuccess = false;
        this.quantityIsNull = true;
      } else {
        const order: OrderModel = {
          skuCode: product.skuCode,
          price: product.price,
          quantity: Number(quantity),
          userDetails: userDetails
        }
        this.orderService.createOrder(order).subscribe({
          next: (value) => {
            console.log("orderService: createOrder result value:" + value);
            this.orderSuccess = true;
          },
          error: (err) => {
            console.log("orderService: createOrder ERROR:" + err);
            this.orderFailed = true;
          },
          complete: () => {console.log("orderService: createOrder complete")}})
      }
    })
  }
}
