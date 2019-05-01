@extends('layouts.admin')

@section('title', trans('reports.daily_summary'))

@section('new_button')
<span class="new-button"><a href="{{ url('reports/profit-loss') }}?print=1&status={{ request('status') }}&year={{ request('year', $this_year) }}" target="_blank" class="btn btn-success btn-sm"><span class="fa fa-print"></span> &nbsp;{{ trans('general.print') }}</a></span>
@endsection

@section('content')
<!-- Default box -->
<div class="box box-success">
    <div class="box-header with-border">
        <h1>Resumen</h1>
        {!! Form::open(['url' => 'reports/daily', 'role' => 'form', 'method' => 'GET']) !!}
        <div id="items" class="pull-left" style="margin-left: 5px">
            {{ Form::date('fecha', Carbon\Carbon::now(),['class' => 'form-control input-filter']) }}
            {!! Form::button('<span class="fa fa-filter"></span> &nbsp;' . trans('general.filter'), ['type' => 'submit', 'class' => 'btn btn-sm btn-default btn-filter']) !!}
        </div>
        {!! Form::close() !!}
    </div>
    <table class="table table-striped table-hover">
    <thead>
      <tr>
        <th>Factura</th>
        <th>Estado</th>
        <th>Monto</th>
      </tr>
    </thead>
    <tbody>
      @php
        $sum=0;
      @endphp
      @foreach ($data as $key => $value)
        @php
          $sum=$sum+$value->amount;
        @endphp
        <tr>
          <td>{{$value->invoice_number}}</td>
          <td>{{$value->invoice_status_code}}</td>
          <td>{{$value->amount}}</td>
        </tr>
      @endforeach
    </tbody>
    <tfoot>
      <tr>
        <td></td>
        <td><h2>Total venta diario:</h2></td>
        <td><h2 class="text-danger">{{$sum}}</h2></td>
      </tr>
    </tfoot>
    </table>

</div>
<!-- /.box -->
@endsection
