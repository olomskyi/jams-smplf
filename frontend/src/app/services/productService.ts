import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProductModel } from '../model/productModel';

@Injectable({
  providedIn: 'root',
})
export class ProductService {

  constructor(private httpClient: HttpClient) {}

  getProducts(): Observable<Array<ProductModel>> {
    return this.httpClient.get<Array<ProductModel>>('http://localhost:9000/api/products');
  }

  createProduct(product: ProductModel): Observable<ProductModel> {
    return this.httpClient.post<ProductModel>('http://localhost:9000/api/products', product);
  }
}
