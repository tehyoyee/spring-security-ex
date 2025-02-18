// src/components/NavBar.js
import React from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

const NavBarContainer = styled.nav`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  background-color: #007bff;
  color: white;
`;

const Logo = styled.div`
  font-size: 1.5rem;
  font-weight: bold;
`;

const Menu = styled.ul`
  display: flex;
  list-style: none;
  margin: 0;
  padding: 0;

  & > li {
    margin-left: 1.5rem;
  }
`;

const MenuItem = styled(Link)`
  text-decoration: none;
  color: white;
  font-size: 1rem;
  font-weight: 500;
  transition: color 0.3s;

  &:hover {
    color: #cce7ff;
  }
`;

const NavBar = () => {
  return (
    <NavBarContainer>
      <Logo>MyApp</Logo>
      <Menu>
        <li>
          <MenuItem to="/">Home</MenuItem>
        </li>
        <li>
          <MenuItem to="/signup">Sign Up</MenuItem>
        </li>
        <li>
          <MenuItem to="/login">Login</MenuItem>
        </li>
      </Menu>
    </NavBarContainer>
  );
};

export default NavBar;
