# Design document

This document keeps *important* decisions or assumptions that define implementation for this project.

## Assumptions

 * Performance is not an issue because project is designed for persoanal use
 * Each vacancy is described as a separate document. Because project is designed for personal use (again!)
 * Vacancy data format is work in progress and will be changed if necessary

## Vacancy data

Vacancy entity (be it for creating one or from read operation) must have fields:

 * id  that gives unique identifier to a vacancy in vacancy-explorer
 * vacancy_name - title for position (mostly)
 * description - gives the most information about duties, technologies used, requirements, etc.
 * source_name - identifies where vacancy came from (e.g. job site, friend suggestion, forum, etc.)
 * corresponding_id - identifies vacancy in source system (may be useful for verification, update, additional info, etc.)
 * creation_timestamp - time when vacancy was added to vacancy-explorer

These fields would be really nice to have:

 * corresponding_creation_timestamp - time when vacancy was added in source system (e.g. published by company on source site)
 * company_name - identifies company that provided vacancy information (be it a company, recruiting agency, etc.)
 * company_hq_country - where HQ of company is located
 * customers_main_country - who are the main customers for companies products (e.g. Russia, EU, Worldwide, etc.)
 * salary - information about salary range, currency, yearly/monthly amount, before/after taxes
 * working_office_address - city, street, house number (or home office if position is remote) 