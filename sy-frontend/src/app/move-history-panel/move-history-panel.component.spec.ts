import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { MoveHistoryPanelComponent } from './move-history-panel.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatGridListModule } from '@angular/material/grid-list';
import { NgIf, NgForOf } from '@angular/common';

describe('MoveHistoryPanelComponent', () => {
  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [
        MoveHistoryPanelComponent,
        HttpClientTestingModule,
        MatGridListModule,
        NgIf,
        NgForOf
      ]
    })
    .compileComponents();
  }));

  it('should create', () => {
    const fixture = TestBed.createComponent(MoveHistoryPanelComponent);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
