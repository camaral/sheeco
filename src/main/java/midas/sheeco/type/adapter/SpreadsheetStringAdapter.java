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

package midas.sheeco.type.adapter;

import java.text.DecimalFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

public class SpreadsheetStringAdapter
    implements
        SpreadsheetTypeAdapter<String>

{
    public static final String DECIMAL_PATTERN = "0.###########";
    private static final DecimalFormat decimalFormat = new DecimalFormat( DECIMAL_PATTERN );

    @Override
    public String fromSpreadsheet(
        final Cell cell )
    {
        switch( cell.getCellType() ) {
            case Cell.CELL_TYPE_STRING:
                final String value = cell.getRichStringCellValue().getString().trim();
                return ! value.isEmpty() ? value : null;
            case Cell.CELL_TYPE_NUMERIC:
                if( DateUtil.isCellDateFormatted( cell ) ) {
                    return String.valueOf( SpreadsheetDateAdapter.DATE_PATTERNS[ 0 ].format( cell.getDateCellValue() ) );
                } else {
                    return decimalFormat.format( cell.getNumericCellValue() );
                }
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf( cell.getBooleanCellValue() );
            case Cell.CELL_TYPE_BLANK:
                return null;
            case Cell.CELL_TYPE_ERROR:
            case Cell.CELL_TYPE_FORMULA:
            default:
                throw new InvalidCellFormatException( "The cell type: " + cell.getCellType()
                    + " is either not supported or not possible" );
        }
    }
}
