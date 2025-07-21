import { MatchComponent } from './match.component';
import { BoardComponent } from '../board/board.component';
import { MatchStatePanelComponent } from '../match-state-panel/match-state-panel.component';
import { DetectivesPanelComponent } from '../detectives-panel/detectives-panel.component';
import { MoveHistoryPanelComponent } from '../move-history-panel/move-history-panel.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgIf, NgForOf } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { TestBed } from '@angular/core/testing';

describe('MatchComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        MatchComponent,
        BoardComponent,
        MatchStatePanelComponent,
        DetectivesPanelComponent,
        MoveHistoryPanelComponent,
        HttpClientTestingModule,
        NgIf,
        NgForOf
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            paramMap: of({ get: () => '1' })
          }
        }
      ]
    }).compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(MatchComponent);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
