import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { MatLabel } from '@angular/material/form-field';

import {Board} from './board';
import {MatchConfiguration} from './match-configuration';
import {BoardService} from '../board.service';
import {MatchManagementService} from '../match-management.service';

@Component({
  selector: 'app-matchconfig',
  templateUrl: './matchconfig.component.html',
  styleUrls: ['./matchconfig.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatOptionModule,
    MatLabel
  ]
})
export class MatchconfigComponent implements OnInit {

  boards : Board[];
  selectedBoard : Board;
  createdMatchUUID = '';
  configForm = this.fb.group({
	boardId: [''],
	startingPositions: [''],
	surfacingTurns: [''],
	nrOfDetectives: [''],
	nrOfTurns: [''],
	taxiPerDetective: [''],
	busPerDetective: [''],
	undergroundPerDetective: [''],
	blackPerDetective: [''],
	taxiForMrX: [''],
	busForMrX: [''],
	undergroundForMrX: [''],
	blackForMrX: ['']
  });
  nameForm = this.fb.group({
	  boardId: [''],
	  name: ['']
  });

  constructor(
	private boardService: BoardService,
	private mmService: MatchManagementService,
	private fb: FormBuilder,
	private router: Router) { }

  ngOnInit(): void {
	  this.getBoards();
  }

  onSelect(board : Board) : void {
	  this.selectedBoard = board;
	  this.getDefaultConfiguration();
  }
  
  onSubmit() : void {
	  const formValue = this.configForm.getRawValue();
	  const config: MatchConfiguration = {
		boardId: formValue.boardId,
		startingPositions: formValue.startingPositions ? JSON.parse(formValue.startingPositions) : [],
		surfacingTurns: formValue.surfacingTurns ? JSON.parse(formValue.surfacingTurns) : [],
		nrOfDetectives: parseInt(formValue.nrOfDetectives),
		nrOfTurns: parseInt(formValue.nrOfTurns),
		taxiPerDetective: parseInt(formValue.taxiPerDetective),
		busPerDetective: parseInt(formValue.busPerDetective),
		undergroundPerDetective: parseInt(formValue.undergroundPerDetective),
		blackPerDetective: parseInt(formValue.blackPerDetective),
		taxiForMrX: parseInt(formValue.taxiForMrX),
		busForMrX: parseInt(formValue.busForMrX),
		undergroundForMrX: parseInt(formValue.undergroundForMrX),
		blackForMrX: parseInt(formValue.blackForMrX)
	  };
	  this.mmService.postCreateMatch(config)
		.subscribe(
			data => {
				this.createdMatchUUID = JSON.stringify(data);
				this.setName(data);
				this.router.navigate(['/']);
			}
		);
  }
  
  setName(id : String) : void {
	  this.mmService.postMatchName(
		id, this.nameForm.controls['name'].value)
			.subscribe(
				data => {}
			);
  }
  
  getBoards(): void { 
	  this.boardService.getBoards()
		.subscribe(
			data => {
				this.boards = data;
			}
		);
  }
  
  getDefaultConfiguration() : void {
	  this.boardService.getDefaultConfiguration(this.selectedBoard.id)
		.subscribe(
			data => {
				// Convert the data to string format for the form
				const formData = {
					boardId: data.boardId.toString(),
					startingPositions: JSON.stringify(data.startingPositions),
					surfacingTurns: JSON.stringify(data.surfacingTurns),
					nrOfDetectives: data.nrOfDetectives.toString(),
					nrOfTurns: data.nrOfTurns.toString(),
					taxiPerDetective: data.taxiPerDetective.toString(),
					busPerDetective: data.busPerDetective.toString(),
					undergroundPerDetective: data.undergroundPerDetective.toString(),
					blackPerDetective: data.blackPerDetective.toString(),
					taxiForMrX: data.taxiForMrX.toString(),
					busForMrX: data.busForMrX.toString(),
					undergroundForMrX: data.undergroundForMrX.toString(),
					blackForMrX: data.blackForMrX.toString()
				};
				this.configForm.setValue(formData);
			}
		);
  }
}
