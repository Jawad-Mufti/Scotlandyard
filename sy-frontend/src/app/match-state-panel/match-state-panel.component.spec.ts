import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { MatchStatePanelComponent } from './match-state-panel.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CommonModule } from '@angular/common';
import { NgIf, NgForOf } from '@angular/common';

describe('MatchStatePanelComponent', () => {
  let component: MatchStatePanelComponent;
  let fixture: ComponentFixture<MatchStatePanelComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [
        MatchStatePanelComponent,
        HttpClientTestingModule,
        CommonModule,
        NgIf,
        NgForOf
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MatchStatePanelComponent);
    component = fixture.componentInstance;
    // Provide mock input for state
    component.state = { playerStates: [], matchId: 1, turn: 1 } as any;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
