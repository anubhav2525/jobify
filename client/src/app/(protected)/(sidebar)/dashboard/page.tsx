import { getSelectedCountry } from "@/shared/enum/country-code";
import React from "react";

const DashboardPage = () => { 
  const selected = getSelectedCountry("India");
  console.log(selected)
  return <div>DashboardPage</div>;
};

export default DashboardPage;
