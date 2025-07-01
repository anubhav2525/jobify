import React from "react";

const Home = () => {
  return (
    <section className="gradient-bg min-h-screen flex items-center justify-center relative overflow-hidden">
      <div className="absolute inset-0 overflow-hidden pointer-events-none">
        <div className="absolute top-20 left-10 w-20 h-20 bg-white opacity-10 rounded-full"></div>
        <div className="absolute top-40 right-20 w-16 h-16 bg-white opacity-20 rounded-full animate-float"></div>
        <div className="absolute bottom-40 left-20 w-12 h-12 bg-white opacity-10 rounded-full"></div>
        <div className="absolute bottom-20 right-10 w-24 h-24 bg-white opacity-10 rounded-full animate-pulse"></div>
      </div>

      {/* section for home page */}
      <div className="w-full min-h-screen grid-cols-1 lg:grid-cols-2 gap-10 ">
        <div className="bg-white rounded-lg shadow-lg p-6 flex flex-col items-center justify-center text-center h-full w-full">oi</div>
        <div className="bg-white rounded-lg shadow-lg p-6 flex flex-col items-center justify-center text-center h-full"></div>
      </div>
    </section>
  );
};

export default Home;
