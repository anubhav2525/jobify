"use client";
import React from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader } from "@/components/ui/card";
import { Lens } from "@/components/ui/lens";
import { BoxReveal } from "@/components/ui/box-reveal";

const AuthIntroSection = () => {
  return (
    <Card className="relative h-full w-fit shadow-none rounded-none bg-white/40 backdrop-blur-md border-none">
      <CardHeader >
        <Lens
          zoomFactor={2}
          lensSize={150}
          isStatic={false}
          ariaLabel="Zoom Area"
        >
          <img
            src="/assets/images/app-layout/auth-intro.webp"
            alt="image placeholder"
            width={500}
            height={500}
            className="object-cover rounded-lg"
          />
        </Lens>
      </CardHeader>
      <CardContent>
        <IntroCard />
      </CardContent>
    </Card>
  );
};

export default AuthIntroSection;

const IntroCard = () => {
  return (
    <div className="size-full items-center justify-center overflow-hidden">
      <BoxReveal boxColor={"#9810fa"} duration={0.5}>
        <p className="text-3xl font-semibold">
          Magic UI<span className="text-[#5046e6]">.</span>
        </p>
      </BoxReveal>

      <BoxReveal boxColor={"#9810fa"} duration={0.5}>
        <h2 className="mt-[.5rem] text-[1rem]">
          UI library for{" "}
          <span className="text-[#5046e6]">Design Engineers</span>
        </h2>
      </BoxReveal>

      <BoxReveal boxColor={"#9810fa"} duration={0.5}>
        <div className="mt-6">
          <p>
            -&gt; 20+ free and open-source animated components built with
            <span className="font-semibold text-[#5046e6]">React</span>,
            <span className="font-semibold text-[#5046e6]">Typescript</span>,
            <span className="font-semibold text-[#5046e6]">Tailwind CSS</span>,
            and
            <span className="font-semibold text-[#5046e6]">Motion</span>
            . <br />
            -&gt; 100% open-source, and customizable. <br />
          </p>
        </div>
      </BoxReveal>

      <BoxReveal boxColor={"#9810fa"} duration={0.5}>
        <Button className="mt-4 bg-purple-500 hover:bg-purple-600">
          Explore
        </Button>
      </BoxReveal>
    </div>
  );
};
