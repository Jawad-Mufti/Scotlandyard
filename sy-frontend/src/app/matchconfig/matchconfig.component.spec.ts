import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { MatchconfigComponent } from './matchconfig.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { NgIf, NgForOf } from '@angular/common';

describe('MatchconfigComponent', () => {
  let component: MatchconfigComponent;
  let fixture: ComponentFixture<MatchconfigComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [
        MatchconfigComponent,
        HttpClientTestingModule,
        MatFormFieldModule,
        MatInputModule,
        MatSelectModule,
        MatOptionModule,
        NgIf,
        NgForOf
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MatchconfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(MatchconfigComponent);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
