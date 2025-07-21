import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { MatchListComponent } from './match-list.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { NgIf, NgForOf } from '@angular/common';

describe('MatchListComponent', () => {
  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [
        MatchListComponent,
        HttpClientTestingModule,
        MatTableModule,
        MatFormFieldModule,
        MatInputModule,
        NgIf,
        NgForOf
      ]
    })
    .compileComponents();
  }));

  it('should create', () => {
    const fixture = TestBed.createComponent(MatchListComponent);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
