import React, { createContext, useContext, useState, ReactNode, useEffect } from 'react';

// todo file

interface AuthContextProps {
  isLoggedIn: boolean;
  login: (userName: string, userId: string, userRealName: string, userPassword: string) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextProps | undefined>(undefined);
interface AuthProviderProps {
  children: ReactNode;
}
export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);

  useEffect(() => {
    const userName = sessionStorage.getItem('mallUserName');
    setIsLoggedIn(!!userName);
  }, []);

  const login = (userName: string, userId: string, userRealName: string, userPassword: string) => {
    sessionStorage.setItem('mallUserName', userName);
    sessionStorage.setItem('mallUserId', userId);
    sessionStorage.setItem('mallUserRealName', userRealName);
    sessionStorage.setItem('mallUserPassword', userPassword);
    setIsLoggedIn(true);
  };

  const logout = () => {
    sessionStorage.removeItem('mallUserName');
    setIsLoggedIn(false);
  };

  return (
    <AuthContext.Provider value={{ isLoggedIn, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};