import { TestBed, waitForAsync } from '@angular/core/testing';
import { MatchComponent } from './match/match.component';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CommonModule } from '@angular/common';

describe('MatchComponent', () => {
  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [
        MatchComponent,
        RouterTestingModule,
        HttpClientTestingModule,
        CommonModule
      ],
      providers: [
        { provide: ActivatedRoute, useValue: {} }
      ]
    }).compileComponents();
  }));

  it('should create', () => {
    const fixture = TestBed.createComponent(MatchComponent);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
}); 