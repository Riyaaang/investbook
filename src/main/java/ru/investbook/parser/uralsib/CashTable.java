/*
 * InvestBook
 * Copyright (C) 2020  Vitalii Ananev <an-vitek@ya.ru>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ru.investbook.parser.uralsib;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.spacious_team.table_wrapper.api.*;
import ru.investbook.parser.AbstractReportTable;
import ru.investbook.parser.PortfolioCash;

import java.util.Collection;

import static java.util.Collections.singletonList;
import static ru.investbook.parser.uralsib.CashTable.CashTableHeader.CURRENCY;
import static ru.investbook.parser.uralsib.CashTable.CashTableHeader.VALUE;

@Slf4j
public class CashTable extends AbstractReportTable<PortfolioCash> {

    private static final String TABLE_NAME = "ПОЗИЦИЯ ПО ДЕНЕЖНЫМ СРЕДСТВАМ";

    public CashTable(UralsibBrokerReport report) {
        super(report, TABLE_NAME, "", CashTableHeader.class, 2);
    }

    @Override
    protected Collection<PortfolioCash> getRow(Table table, TableRow row) {
        return singletonList(PortfolioCash.builder()
                .section("all")
                .value(table.getCurrencyCellValue(row, VALUE))
                .currency(UralsibBrokerReport.convertToCurrency(table.getStringCellValue(row, CURRENCY)))
                .build());
    }

    enum CashTableHeader implements TableColumnDescription {
        VALUE("исходящий остаток"),
        CURRENCY("код валюты");

        @Getter
        private final TableColumn column;
        CashTableHeader(String ... words) {
            this.column = TableColumnImpl.of(words);
        }
    }
}
