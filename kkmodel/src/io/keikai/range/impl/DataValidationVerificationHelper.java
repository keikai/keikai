/*

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		
}}IS_NOTE

Copyright (C) 2015 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package io.keikai.range.impl;

import java.io.Serializable;
import java.util.Date;

import io.keikai.model.InvalidDataValidationException;
import io.keikai.model.SCell;
import io.keikai.model.SCellStyle;
import io.keikai.model.SDataValidation;
import io.keikai.model.sys.EngineFactory;
import io.keikai.model.sys.input.InputEngine;
import io.keikai.model.sys.input.InputParseContext;
import io.keikai.model.sys.input.InputResult;
import org.zkoss.poi.ss.usermodel.ZssContext;

/**
 * 
 * @author JerryChen
 * @since 3.8.0
 */
//ZSS-981
public class DataValidationVerificationHelper implements Serializable {
	private static final long serialVersionUID = -5730156448383762080L;
	
	SDataValidation.ValidationType _validationType;
	boolean _ignoreBlank; 
	SDataValidation.OperatorType _operatorType;
	boolean _inCellDropDown; 
	String _formula1; 
	String _formula2;
	boolean _showInput; 
	String _inputTitle; 
	String _inputMessage;
	boolean _showError;
	SDataValidation.AlertStyle _alertStyle;
	String _errorTitle;
	String _errorMessage;
	
	boolean _doubleFormula;
	
	public DataValidationVerificationHelper(
			SDataValidation.ValidationType validationType,
			boolean ignoreBlank, SDataValidation.OperatorType operatorType,
			boolean inCellDropDown, String formula1, String formula2,
			boolean showInput, String inputTitle, String inputMessage,
			boolean showError, SDataValidation.AlertStyle alertStyle, String errorTitle,
			String errorMessage) {
		
		_validationType = validationType;
		_ignoreBlank = ignoreBlank;
		_operatorType = operatorType;
		_inCellDropDown = inCellDropDown;
		_formula1 = formula1;
		_formula2 = formula2;
		_showInput = showInput;
		_inputTitle = inputTitle;
		_inputMessage = inputMessage;
		_showError = showError;
		_alertStyle = alertStyle;
		_errorTitle = errorTitle;
		_errorMessage = errorMessage;
		
		_doubleFormula = _operatorType == SDataValidation.OperatorType.BETWEEN || _operatorType == SDataValidation.OperatorType.NOT_BETWEEN;
	}

	public void verify() {
		final InputEngine ie = EngineFactory.getInstance().createInputEngine();
		InputResult formula1Value = ie.parseInput(_formula1 == null ? ""
				: _formula1, SCellStyle.FORMAT_GENERAL, new InputParseContext(ZssContext.getCurrent().getLocale()));
		InputResult formula2Value = ie.parseInput(_formula2 == null ? ""
				: _formula2, SCellStyle.FORMAT_GENERAL, new InputParseContext(ZssContext.getCurrent().getLocale()));
		
		switch (_validationType) {
			case INTEGER:
				verifyInteger(formula1Value, formula2Value);
				break;
			case TEXT_LENGTH:
				verifyTextLength(formula1Value, formula2Value);
				break;
			case DECIMAL:
				verifyDecimal(formula1Value, formula2Value);
				break;
			case DATE:
				verifyDate(formula1Value, formula2Value);
				break;
			case TIME:
				verifyTime(formula1Value, formula2Value);
				break;
			case LIST:
			case CUSTOM:
			case ANY:
				// skip
		}
	}

	private void verifyDate(InputResult formula1Value, InputResult formula2Value) {
		boolean notFormula2 = !isFormula(formula2Value);
		boolean notFormula1 = !isFormula(formula1Value);
		
		if(_doubleFormula) {
			if(notFormula2 && !formula2Value.getValue().getClass().equals(Date.class)) {
				throw new InvalidDataValidationException("Formula should be a Date value.");
			}
		}
		
		if(notFormula1 && !formula1Value.getValue().getClass().equals(Date.class)) {
			throw new InvalidDataValidationException("Formula should be a Date value.");
		}
		
		if(notFormula2 && notFormula1 && _doubleFormula && ((Date) formula2Value.getValue()).getTime() < ((Date) formula1Value.getValue()).getTime()) {
			throw new InvalidDataValidationException("The End Date must be greater than or equal to the Start Date.");
		}
	}
	
	private void verifyTime(InputResult formula1Value, InputResult formula2Value) {
		boolean notFormula2 = !isFormula(formula2Value);
		boolean notFormula1 = !isFormula(formula1Value);
		
		if(_doubleFormula) {
			if(notFormula2 && !formula2Value.getValue().getClass().equals(Date.class)) {
				throw new InvalidDataValidationException("Formula should be a Time value.");
			}
		}
		
		if(notFormula1 && !formula1Value.getValue().getClass().equals(Date.class)) {
			throw new InvalidDataValidationException("Formula should be a Time value.");
		}
		
		if(notFormula2 && notFormula1 && _doubleFormula && ((Date) formula2Value.getValue()).getTime() < ((Date) formula1Value.getValue()).getTime()) {
			throw new InvalidDataValidationException("The End Time must be greater than or equal to the Start Time.");
		}
	}

	private void verifyDecimal(InputResult formula1Value, InputResult formula2Value) {
		boolean notFormula2 = !isFormula(formula2Value);
		boolean notFormula1 = !isFormula(formula1Value);
		
		if(_doubleFormula) {
			if(notFormula2 && formula2Value.getType() != SCell.CellType.NUMBER) {
				throw new InvalidDataValidationException("Formula should be a Decimal value.");
			}
		}
		
		if(notFormula1 && formula1Value.getType() != SCell.CellType.NUMBER) {
			throw new InvalidDataValidationException("Formula should be a Decimal value.");
		}
		
		if(notFormula2 && notFormula1 && _doubleFormula && ((Double) formula2Value.getValue()) < ((Double) formula1Value.getValue())) {
			throw new InvalidDataValidationException("The Maximum must be greater than or equal to the Minimum.");
		}
	}

	private void verifyInteger(InputResult formula1Value, InputResult formula2Value) {
		try {	
			boolean notFormula2 = !isFormula(formula2Value);
			boolean notFormula1 = !isFormula(formula1Value);
			Double value;
			
			if(_doubleFormula) {
				if(notFormula2 && (formula2Value.getType() != SCell.CellType.NUMBER
						|| (value = (Double) formula2Value.getValue()) != value.intValue())) {
					throw new InvalidDataValidationException("Formula should be a Whole number.");
				}
			}
			
			if(notFormula1 && (formula1Value.getType() != SCell.CellType.NUMBER
					|| (value = (Double) formula1Value.getValue()) != value.intValue())) {
				throw new InvalidDataValidationException("Formula should be a Whole number.");
			}
			
			if(notFormula2 && notFormula1 && _doubleFormula && ((Double) formula2Value.getValue()) < ((Double) formula1Value.getValue())) {
				throw new InvalidDataValidationException("The Maximum must be greater than or equal to the Minimum.");
			}
		} catch (ClassCastException e) {
			throw new InvalidDataValidationException("Formula should be a Whole number.");
		}
	}
	
	private void verifyTextLength(InputResult formula1Value, InputResult formula2Value) {
		try {	
			boolean notFormula2 = !isFormula(formula2Value);
			boolean notFormula1 = !isFormula(formula1Value);
			Double value;
			
			if(_doubleFormula) {
				if(notFormula2 && (formula2Value.getType() != SCell.CellType.NUMBER
						|| (value = (Double) formula2Value.getValue()) != value.intValue())) {
					throw new InvalidDataValidationException("Formula should be a integer.");
				}
			}
			
			if(notFormula1 && (formula1Value.getType() != SCell.CellType.NUMBER
					|| (value = (Double) formula1Value.getValue()) != value.intValue())) {
				throw new InvalidDataValidationException("Formula should be a integer.");
			}
			
			if(notFormula2 && notFormula1 && _doubleFormula && ((Double) formula2Value.getValue()) < ((Double) formula1Value.getValue())) {
				throw new InvalidDataValidationException("The Maximum must be greater than or equal to the Minimum.");
			}
		} catch (ClassCastException e) {
			throw new InvalidDataValidationException("Formula should be a integer.");
		}
	}

	private boolean isFormula(InputResult formulaValue) {
		return formulaValue.getType() == SCell.CellType.FORMULA;
	}
}
