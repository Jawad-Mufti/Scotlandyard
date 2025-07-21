import { TestBed } from '@angular/core/testing';
import { MatchManagementService } from './match-management.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('MatchManagementService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    TestBed.inject(MatchManagementService);
  });

  it('should be created', () => {
    const service: MatchManagementService = TestBed.inject(MatchManagementService);
    expect(service).toBeTruthy();
  });
});
