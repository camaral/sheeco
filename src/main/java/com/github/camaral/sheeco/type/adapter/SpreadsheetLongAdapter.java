/*
 * Copyright 2011-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.camaral.sheeco.type.adapter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

public class SpreadsheetLongAdapter
    implements
        SpreadsheetTypeAdapter<Long>
{

    @Override
    public Long fromSpreadsheet(
        final Cell cell )
    {
        switch( cell.getCellType() ) {
            case Cell.CELL_TYPE_STRING:
                try {
                    return Long.valueOf( cell.getRichStringCellValue().getString().trim() );
                } catch( final NumberFormatException e ) {
                    throw new InvalidCellValueException();
                }
            case Cell.CELL_TYPE_NUMERIC:
                if( DateUtil.isCellDateFormatted( cell ) ) {
                    throw new InvalidCellValueException();
                } else {
                    final double value = cell.getNumericCellValue();
                    final double floor = Math.floor( value );
                    if( value == floor ) {
                        return Long.valueOf( (long) value );
                    } else {
                        throw new InvalidCellValueException();
                    }
                }
            case Cell.CELL_TYPE_BLANK:
                return null;
            case Cell.CELL_TYPE_BOOLEAN:
            case Cell.CELL_TYPE_ERROR:
            case Cell.CELL_TYPE_FORMULA:
            default:
                throw new InvalidCellFormatException( "The cell type: " + cell.getCellType()
                    + " is either not supported or not possible" );
        }
    }
}