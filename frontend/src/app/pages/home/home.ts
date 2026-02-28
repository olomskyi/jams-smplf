import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { OrderService } from '../../services/orderService';
import { ProductService } from '../../services/productService';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { OrderModel } from '../../model/orderModel';
import { ProductModel } from '../../model/productModel';

@Component({
  selector: 'app-home',
  imports: [FormsModule],
  templateUrl: './home.html',
  standalone: true,
  styleUrl: './home.css',
})
export class Home implements OnInit {
  private readonly oidcSecurityService = inject(OidcSecurityService);
  private readonly productService = inject(ProductService);
  private readonly orderService = inject(OrderService);
  private readonly router = inject(Router);
  private readonly cdr = inject(ChangeDetectorRef);
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
            console.log('=== PRODUCTS RECEIVED ===');
            console.log('Count:', product.length);
            console.log('Data:', product);
            console.log('========================');
            this.products = product;
            this.cdr.markForCheck();
          })
      })
  }

  goToCreateProductPage() {
    this.router.navigateByUrl('/product');
  }

  createOrder(product: ProductModel, quantity: string): void {
    const quantityNum = Number(quantity);

    console.log('createOrder: Product = ', product);

    this.oidcSecurityService.userData$.subscribe(result => {
      console.log("User Data: " + result);
      const userDetails = {
        email: result.userData.preferred_username,
        firstName: result.userData.given_name,
        lastName: result.userData.family_name
      };

      if (quantityNum == null || quantityNum <= 0) {
        this.orderFailed = true;
        this.orderSuccess = false;
        this.quantityIsNull = true;
      } else {
        const order: OrderModel = {
          skuCode: product.skuCode,
          price: product.price,
          quantity: quantityNum,
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
