import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AppService} from '../app.service';
import {AirQuality} from '../airquality';
import {Statistics} from '../statistics';

@Component({
  selector: 'app-air-quality',
  templateUrl: './air-quality.component.html',
  styleUrls: ['./air-quality.component.css']
})
export class AirQualityComponent implements OnInit {
  countryForm: FormGroup;
  countries: string[] = [];
  states: string[] = [];
  cities: string[] = [];
  airQuality: AirQuality = new AirQuality();
  stats: Statistics = new Statistics();

  constructor(private service: AppService) {}

  ngOnInit(): void {
    this.service.getCountries().subscribe(x => this.countries = x);
    this.countryForm = new FormGroup({
      country: new FormControl('', [Validators.required]),
      state: new FormControl('', [Validators.required]),
      city: new FormControl('', [Validators.required])
    });
    this.countryForm.get('country').valueChanges.subscribe(newValue => {
      this.countryForm.value.state = '';
      this.cities = [];
      this.service.getStates(newValue).subscribe(x => this.states = x);
    });
    this.countryForm.get('state').valueChanges.subscribe(newValue => {
      this.countryForm.value.city = '';
      this.service.getCities(this.countryForm.value.country, newValue).subscribe(x => this.cities = x);
    });
    this.service.getStatistics().subscribe(stats => this.stats = stats);
  }

  getData(): void {
    this.service.getAirQuality(this.countryForm.value.country, this.countryForm.value.state, this.countryForm.value.city)
      .subscribe(x => this.airQuality = x);
  }

  getStatistics(): void {
    this.service.getStatistics().subscribe(stats => this.stats = stats);
  }

}
