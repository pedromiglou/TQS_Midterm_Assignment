import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AirQuality} from './airquality';
import {Statistics} from './statistics';
import {baseURL} from './../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AppService {
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  getCountries(): Observable<string[]> {
    return this.http.get<string[]>(baseURL + '/countries', this.httpOptions);
  }

  getStates(country: string): Observable<string[]> {
    return this.http.get<string[]>(baseURL + '/states?country=' + country, this.httpOptions);
  }

  getCities(country: string, state: string): Observable<string[]> {
    return this.http.get<string[]>(baseURL + '/cities?country=' + country + '&state=' + state, this.httpOptions);
  }

  getAirQuality(country: string, state: string, city: string): Observable<AirQuality> {
    return this.http.get<AirQuality>(baseURL + '/airquality?country=' + country + '&state=' + state + '&city=' + city,
      this.httpOptions);
  }

  getStatistics(): Observable<Statistics> {
    return this.http.get<Statistics>(baseURL + '/statistics', this.httpOptions);
  }
}
