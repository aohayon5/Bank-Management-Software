# Bank and Stock Exchange System

## Overview

This project simulates a banking system integrated with a stock exchange. It provides functionalities for managing bank accounts, handling stock transactions, and interacting with stock listings.

## Components

- **Bank**: Manages patrons, their accounts, and stock transactions.
- **Patron**: Represents a customer for the savings and brokerage accounts.
- **BrokerageAccount**: An account that handles stock-related transactions.
- **SavingsAccount**: Models a savings account with deposit and withdrawal capabilities.
- **StockExchange**: Handles stock listings and operations related to the stock market.
- **StockListing**: Represents a stock listing through its price and available shares.
- **StockShares**: Tracks the number of shares a patron owns for a specific stock.
- **StockTransaction**: Enables a buy or sell stock transaction.
- **Transaction**: Interface for all transactions.
- **Exceptions**: Custom exceptions for handling invalid actions.

## Features

- Create and manage stock listings on the stock exchange.
- Open and manage savings and brokerage accounts for patrons.
- Perform various transactions including deposits, withdrawals, stock purchases, and sales.
- Handle errors related to invalid transactions and insufficient assets.
