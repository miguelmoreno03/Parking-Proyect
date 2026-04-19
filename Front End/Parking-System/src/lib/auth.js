let accessToken = null;
let tokenType = null;
let userRole = null;

export function setAuthSession({ accessToken: token, type, role }) {
  accessToken = token;
  tokenType = type;
  userRole = role;
}

export function clearAuthSession() {
  accessToken = null;
  tokenType = null;
  userRole = null;
}

export function getAccessToken() {
  return accessToken;
}

export function getTokenType() {
  return tokenType;
}

export function getUserRole() {
  return userRole;
}