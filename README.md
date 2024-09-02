# Bank and Stock Exchange System

## Overview

This project simulates a banking system integrated with a stock exchange. It provides functionalities for managing bank accounts, handling stock transactions, and managing stock listings.

## Components

- **Bank**: Manages patrons, their accounts, and stock transactions.
- **Patron**: Represents a bank customer who can have a savings account and a brokerage account.
- **SavingsAccount**: Models a savings account with deposit and withdrawal capabilities.
- **BrokerageAccount**: Extends `Account` to handle transactions related to stock trading.
- **StockExchange**: Handles stock listings and operations related to the stock market.
- **StockListing**: Represents a stock listing with its price and available shares.
- **StockShares**: Tracks the quantity of shares a patron owns for a specific stock.
- **StockTransaction**: Represents a stock transaction (buy or sell) with a timestamp.
- **Transaction**: Interface for all transactions, including timestamps and types.
- **Exceptions**: Custom exceptions for handling invalid transactions and insufficient assets.

## Features

- Create and manage stock listings on the stock exchange.
- Open and manage savings and brokerage accounts for patrons.
- Perform and track transactions including deposits, withdrawals, stock purchases, and sales.
- Handle errors related to invalid transactions and insufficient assets.

##  Run the Demo
   - Compile and run the `Demo` class to see the system in action.
   - The `Demo` class provides a sample usage of the system. It demonstrates creating stock listings, opening accounts, making transactions, and printing out the state of the stock exchange and patrons.