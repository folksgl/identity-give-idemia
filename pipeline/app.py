#!/usr/bin/env python3

from aws_cdk import core

from pipeline_stack import PipelineStack


app = core.App()

app_name = "give-ipp-idemia"
pipeline_stack_name = f"{app_name}-pipeline"

PipelineStack(
    app,
    app_name,
    stack_name=pipeline_stack_name,
    repo_owner="pshumateEDC",
    repo_name="identity-give-ipp-idemia",
)

app.synth()
