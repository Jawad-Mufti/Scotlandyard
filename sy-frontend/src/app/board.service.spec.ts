import { TestBed } from '@angular/core/testing';
import { BoardService } from './board.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('BoardService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    TestBed.inject(BoardService);
  });

  it('should be created', () => {
    const service: BoardService = TestBed.inject(BoardService);
    expect(service).toBeTruthy();
  });
});
