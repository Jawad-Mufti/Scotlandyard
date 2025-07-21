import { AvailableMovesComponent } from './available-moves.component';
import { NgIf, NgForOf } from '@angular/common';
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('AvailableMovesComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AvailableMovesComponent, NgIf, NgForOf, HttpClientTestingModule],
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(AvailableMovesComponent);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
