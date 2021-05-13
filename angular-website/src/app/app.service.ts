import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AirQuality} from './airquality';
import {Statistics} from './statistics';

@Injectable({
  providedIn: 'root'
})
export class AppService {
  private baseURL = 'http://localhost:8080/api';

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'access-control-allow-origin': 'http://localhost:8080/'
    })
  };

  constructor(private http: HttpClient) { }

  getCountries(): Observable<string[]> {
    return this.http.get<string[]>(this.baseURL + '/countries', this.httpOptions);
  }

  getStates(country: string): Observable<string[]> {
    return this.http.get<string[]>(this.baseURL + '/states?country=' + country, this.httpOptions);
  }

  getCities(country: string, state: string): Observable<string[]> {
    return this.http.get<string[]>(this.baseURL + '/cities?country=' + country + '&state=' + state, this.httpOptions);
  }

  getAirQuality(country: string, state: string, city: string): Observable<AirQuality> {
    return this.http.get<AirQuality>(this.baseURL + '/airquality?country=' + country + '&state=' + state + '&city=' + city,
      this.httpOptions);
  }

  getStatistics(): Observable<Statistics> {
    return this.http.get<Statistics>(this.baseURL + '/statistics', this.httpOptions);
  }
}
