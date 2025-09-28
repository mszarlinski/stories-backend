# Release Notes - Version 2.0.0

## 🚀 Major Framework Upgrade

### Spring Boot 3.5.6 Migration
- **Upgraded Spring Boot**: From 2.4.0 to 3.5.6
- **Java 17 Required**: Updated minimum Java version from 11 to 17
- **Gradle 9.1.0**: Updated Gradle wrapper for better performance and security

### Security Configuration Modernization
- Migrated from deprecated `WebSecurityConfigurerAdapter` to modern `SecurityFilterChain`
- Updated security DSL to use new lambda-based configuration
- Improved OAuth2 resource server configuration

### Dependency Updates
- **Testcontainers**: Updated to 1.21.3 (from 1.15.0)
- **Awaitility**: Updated to 4.3.0 (from 4.0.3)
- **Spring Dependency Management**: Updated to 1.1.7

## ✨ New Features

### Input Validation System
- **Story Validation**: Added comprehensive input validation for story publishing
- **Custom Exceptions**: Implemented domain-specific exception classes:
  - `EmptyTitleException`: Validates story titles are not empty
  - `EmptyContentException`: Ensures story content is provided
  - `InvalidAuthorException`: Validates author information
  - `InvalidStoryException`: Base exception for story validation errors

### Enhanced Error Handling
- **HTTP Status Codes**: Proper 400 Bad Request responses for validation errors
- **Controller Improvements**: Enhanced `PublisherController` with better error handling
- **User Experience**: Clear feedback for invalid story submissions

## 🔧 Technical Improvements

### Code Modernization
- **Collection Methods**: Updated to use modern Java collection APIs (`toList()`)
- **Annotation Updates**: Removed deprecated `@ConstructorBinding` annotations
- **Persistence Annotations**: Updated to `@PersistenceCreator` for Spring Data compatibility

### Architecture Enhancements
- **Domain Validation**: Moved validation logic to application layer
- **Clean Code**: Improved separation of concerns
- **Type Safety**: Enhanced type safety throughout the codebase

## 🧪 Testing

### New Test Suites
- **Publishing Acceptance Tests**: `PublishingAcceptanceTestsClaudeCode`
- **Domain Unit Tests**: `PublishingTestsClaudeCode`
- **Validation Coverage**: Tests for all new validation scenarios

## 📋 Breaking Changes

⚠️ **Important**: This release includes breaking changes due to the Spring Boot 3 migration:

- **Java 17 Required**: Applications must run on Java 17 or higher
- **Security Configuration**: Custom security configurations may need updates
- **Dependency Compatibility**: Third-party libraries may require updates for Spring Boot 3 compatibility

## 🐛 Bug Fixes

- Fixed deprecated configuration property bindings
- Resolved compilation issues with modern Spring Data
- Updated method signatures for Spring Security compatibility

## 📚 Documentation

- Added source code analysis documentation
- Included migration guide for Spring Boot 3 upgrade
- Enhanced API documentation with validation requirements

## 🚚 Migration Guide

### For Developers
1. Update Java runtime to version 17+
2. Review custom security configurations
3. Update any deprecated annotations in your extensions
4. Test thoroughly with new validation rules

### For Deployment
1. Ensure production environments run Java 17+
2. Update Docker base images if applicable
3. Review application configurations for deprecated properties

---

**Full Changelog**: Compare changes in commit [6a02ee6](../../commit/6a02ee6)

**Contributors**: This release was developed with assistance from Claude Code