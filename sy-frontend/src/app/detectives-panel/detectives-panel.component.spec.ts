import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetectivesPanelComponent } from './detectives-panel.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgIf, NgForOf } from '@angular/common';

describe('DetectivesPanelComponent', () => {
  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [
        DetectivesPanelComponent,
        HttpClientTestingModule,
        NgIf,
        NgForOf
      ]
    })
    .compileComponents();
  }));

  it('should create', () => {
    const fixture = TestBed.createComponent(DetectivesPanelComponent);
    const component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });
});
