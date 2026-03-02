# 🤝 Contributing to C2H Admin System

Thank you for your interest in contributing! This document provides guidelines for contributing to the project.

## 📋 Table of Contents

- [Code of Conduct](#code-of-conduct)
- [How Can I Contribute?](#how-can-i-contribute)
- [Development Setup](#development-setup)
- [Pull Request Process](#pull-request-process)
- [Coding Standards](#coding-standards)
- [Commit Messages](#commit-messages)

---

## 📜 Code of Conduct

### Our Pledge

- Be respectful and inclusive
- Accept constructive criticism
- Focus on what's best for the community
- Show empathy towards others

### Unacceptable Behavior

- Harassment or discrimination
- Trolling or insulting comments
- Publishing others' private information
- Other unprofessional conduct

---

## 🎯 How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check existing issues. When creating a bug report, include:

- **Clear title and description**
- **Steps to reproduce**
- **Expected vs actual behavior**
- **Screenshots** (if applicable)
- **Environment details** (OS, Android version, etc.)

**Bug Report Template:**

```markdown
**Describe the bug**
A clear description of what the bug is.

**To Reproduce**
Steps to reproduce:
1. Go to '...'
2. Click on '...'
3. See error

**Expected behavior**
What you expected to happen.

**Screenshots**
If applicable, add screenshots.

**Environment:**
- Device: [e.g. OnePlus 9]
- Android Version: [e.g. Android 13]
- App Version: [e.g. 1.0.0]
```

### Suggesting Features

Feature requests are welcome! Please include:

- **Clear use case**
- **Why this feature is needed**
- **Possible implementation approach**
- **Alternatives considered**

**Feature Request Template:**

```markdown
**Is your feature request related to a problem?**
A clear description of the problem.

**Describe the solution you'd like**
What you want to happen.

**Describe alternatives you've considered**
Other solutions you've thought about.

**Additional context**
Any other context or screenshots.
```

### Code Contributions

1. **Fork the repository**
2. **Create a feature branch** (`git checkout -b feature/AmazingFeature`)
3. **Make your changes**
4. **Test thoroughly**
5. **Commit your changes** (`git commit -m 'Add some AmazingFeature'`)
6. **Push to the branch** (`git push origin feature/AmazingFeature`)
7. **Open a Pull Request**

---

## 🛠️ Development Setup

### Prerequisites

- Android Studio (latest version)
- Node.js 16+
- Git
- Firebase account

### Backend Setup

```bash
cd backend
npm install
cp .env.example .env
# Edit .env with your Firebase credentials
npm start
```

### Android Apps Setup

```bash
# Open in Android Studio
# File > Open > admin-panel (or client-app)
# Wait for Gradle sync
# Update Config.java with your API URL
# Build > Build APK
```

### Running Tests

```bash
# Backend tests
cd backend
npm test

# Android tests
./gradlew test
```

---

## 🔄 Pull Request Process

### Before Submitting

- [ ] Code follows project style guidelines
- [ ] Self-review of code completed
- [ ] Comments added for complex code
- [ ] Documentation updated (if needed)
- [ ] No new warnings generated
- [ ] Tests added/updated (if applicable)
- [ ] All tests passing

### PR Title Format

```
[Type] Brief description

Types:
- feat: New feature
- fix: Bug fix
- docs: Documentation changes
- style: Code style changes (formatting, etc.)
- refactor: Code refactoring
- test: Adding/updating tests
- chore: Maintenance tasks
```

**Examples:**
- `[feat] Add SMS scheduling feature`
- `[fix] Resolve device registration issue`
- `[docs] Update API documentation`

### PR Description Template

```markdown
## Description
Brief description of changes.

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
How has this been tested?

## Screenshots (if applicable)
Add screenshots here.

## Checklist
- [ ] Code follows style guidelines
- [ ] Self-reviewed
- [ ] Commented complex code
- [ ] Updated documentation
- [ ] No new warnings
- [ ] Added tests
- [ ] All tests passing
```

---

## 💻 Coding Standards

### Java/Android

```java
// Use meaningful variable names
String deviceId = "abc123";  // Good
String d = "abc123";         // Bad

// Add comments for complex logic
// Calculate battery percentage based on voltage
int batteryPercent = calculateBatteryLevel(voltage);

// Follow Android naming conventions
public class MainActivity extends AppCompatActivity {
    private TextView tvDeviceName;  // Prefix with type
    private static final String TAG = "MainActivity";
}
```

### JavaScript/Node.js

```javascript
// Use const/let, not var
const API_URL = 'https://api.example.com';
let deviceCount = 0;

// Use async/await
async function getDevices() {
  try {
    const response = await fetch(API_URL);
    return await response.json();
  } catch (error) {
    console.error('Error:', error);
  }
}

// Add JSDoc comments
/**
 * Register a new device
 * @param {string} deviceId - Unique device identifier
 * @param {Object} deviceData - Device information
 * @returns {Promise<Object>} Registration response
 */
async function registerDevice(deviceId, deviceData) {
  // Implementation
}
```

### General Guidelines

- **Indentation:** 2 spaces (JavaScript) or 4 spaces (Java)
- **Line length:** Max 100 characters
- **Naming:**
  - Variables: camelCase
  - Constants: UPPER_SNAKE_CASE
  - Classes: PascalCase
- **Comments:** Explain WHY, not WHAT
- **Error handling:** Always handle errors gracefully

---

## 📝 Commit Messages

### Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types

- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation
- `style`: Formatting
- `refactor`: Code restructuring
- `test`: Tests
- `chore`: Maintenance

### Examples

```bash
feat(admin): Add device filtering by status

- Added dropdown to filter devices
- Implemented filter logic in adapter
- Updated UI to show filter state

Closes #123
```

```bash
fix(client): Resolve SMS receiver crash on Android 13

The SMS receiver was crashing due to missing permission check.
Added runtime permission verification before accessing SMS.

Fixes #456
```

---

## 🧪 Testing Guidelines

### Backend Tests

```javascript
describe('Device API', () => {
  it('should register new device', async () => {
    const response = await request(app)
      .post('/api/device/register')
      .send({ deviceId: 'test-123', ... });
    
    expect(response.status).toBe(200);
    expect(response.body.success).toBe(true);
  });
});
```

### Android Tests

```java
@Test
public void testDeviceRegistration() {
    Device device = new Device("test-123", "Test Device");
    assertNotNull(device.getDeviceId());
    assertEquals("Test Device", device.getDeviceName());
}
```

---

## 📚 Documentation

### Code Documentation

- Add JavaDoc/JSDoc for public methods
- Explain complex algorithms
- Document API endpoints
- Update README when adding features

### API Documentation

When adding new endpoints, update `docs/API.md`:

```markdown
### New Endpoint Name

Description of what it does.

**Endpoint:** `POST /api/new-endpoint`

**Request Body:**
\`\`\`json
{
  "param": "value"
}
\`\`\`

**Response:**
\`\`\`json
{
  "success": true,
  "data": {}
}
\`\`\`
```

---

## 🎨 UI/UX Guidelines

- Follow Material Design guidelines
- Maintain consistent color scheme
- Ensure accessibility (contrast, font sizes)
- Test on different screen sizes
- Add loading states
- Handle errors gracefully with user-friendly messages

---

## 🔒 Security Guidelines

- **Never commit:**
  - API keys
  - Passwords
  - Firebase credentials
  - Keystore files
- Use environment variables for sensitive data
- Validate all user inputs
- Sanitize data before database operations
- Use HTTPS for all API calls

---

## 📞 Getting Help

- **Questions?** Open a [Discussion](https://github.com/kishan7878/C2H-Admin-System/discussions)
- **Bugs?** Open an [Issue](https://github.com/kishan7878/C2H-Admin-System/issues)
- **Email:** decentkishan78@gmail.com

---

## 🙏 Recognition

Contributors will be:
- Listed in README
- Mentioned in release notes
- Credited in commit history

---

## 📄 License

By contributing, you agree that your contributions will be licensed under the MIT License.

---

**Thank you for contributing! 🎉**

Every contribution, no matter how small, makes a difference!
