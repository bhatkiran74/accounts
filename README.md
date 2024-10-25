# Employee Management API

This API allows for managing employee information, including creating new employees and fetching their details and tax deductions.

## Table of Contents

1. [Introduction](#introduction)
2. [API Endpoints](#api-endpoints)
   - [Create Employee](#create-employee)
   - [Fetch Employee Details](#fetch-employee-details)
   - [Fetch Employee Tax Deductions](#fetch-employee-tax-deductions)
3. [Data Models](#data-models)
4. [Error Handling](#error-handling)
5. [Contact](#contact)

## Introduction

The Employee Management API allows users to create and retrieve employee information. This is built using Spring Boot and provides endpoints to manage employee data effectively.

## API Endpoints

### Create Employee

- **Endpoint:** `POST /api/v1/employees`
- **Description:** Creates a new employee based on the provided EmployeeDto.
- **Request Body:**
  ```json
  {
      "name": "John Doe",
      "position": "Software Engineer",
      "salary": 60000
  }
