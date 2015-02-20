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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

public class SpreadsheetBooleanAdapter
    implements
        SpreadsheetTypeAdapter<Boolean>

{
    public static final String[] TRUE_VALUES = {
        "true", "yes", "1"
    };

    public static final String[] FALSE_VALUES = {
        "false", "no", "0"
    };

    @Override
    public Boolean fromSpreadsheet(
        final Cell cell )
    {
        switch( cell.getCellType() ) {
            case Cell.CELL_TYPE_STRING:
                final String value = cell.getRichStringCellValue().getString().trim();

                Boolean ret = null;
                for( final String str : TRUE_VALUES ) {
                    if( str.equalsIgnoreCase( value ) ) {
                        ret = Boolean.TRUE;
                        break;
                    }
                }

                if( ret == null ) {
                    for( final String str : FALSE_VALUES ) {
                        if( str.equalsIgnoreCase( value ) ) {
                            ret = Boolean.FALSE;
                            break;
                        }
                    }
                }

                if( ret == null ) {
                    throw new InvalidCellValueException();
                }

                return ret;
            case Cell.CELL_TYPE_NUMERIC:
                if( DateUtil.isCellDateFormatted( cell ) ) {
                    throw new InvalidCellValueException();
                }

                return cell.getNumericCellValue() > 0 ? Boolean.TRUE : Boolean.FALSE;
            case Cell.CELL_TYPE_BOOLEAN:
                return Boolean.valueOf( cell.getBooleanCellValue() );
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
